package com.luckystar.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.domain.LaborUnion;

import com.luckystar.web.domain.User;
import com.luckystar.web.repository.LaborUnionRepository;
import com.luckystar.web.repository.UserRepository;
import com.luckystar.web.security.SecurityUtils;
import com.luckystar.web.service.UserService;
import com.luckystar.web.utils.Tools;
import com.luckystar.web.web.rest.util.HeaderUtil;
import com.luckystar.web.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LaborUnion.
 */
@RestController
@RequestMapping("/api")
public class LaborUnionResource {

    private final Logger log = LoggerFactory.getLogger(LaborUnionResource.class);

    private static final String ENTITY_NAME = "laborUnion";

    private final LaborUnionRepository laborUnionRepository;

    @Autowired
    private UserRepository userRepository;

    public LaborUnionResource(LaborUnionRepository laborUnionRepository) {
        this.laborUnionRepository = laborUnionRepository;
    }

    /**
     * POST  /labor-unions : Create a new laborUnion.
     *
     * @param laborUnion the laborUnion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new laborUnion, or with status 400 (Bad Request) if the laborUnion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/labor-unions")
    @Timed
    public ResponseEntity<LaborUnion> createLaborUnion(@Valid @RequestBody LaborUnion laborUnion) throws URISyntaxException {
        log.debug("REST request to save LaborUnion : {}", laborUnion);
        if (laborUnion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new laborUnion cannot already have an ID")).body(null);
        }
        LaborUnion result = laborUnionRepository.save(laborUnion);
        return ResponseEntity.created(new URI("/api/labor-unions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /labor-unions : Updates an existing laborUnion.
     *
     * @param laborUnion the laborUnion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated laborUnion,
     * or with status 400 (Bad Request) if the laborUnion is not valid,
     * or with status 500 (Internal Server Error) if the laborUnion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/labor-unions")
    @Timed
    public ResponseEntity<LaborUnion> updateLaborUnion(@Valid @RequestBody LaborUnion laborUnion) throws URISyntaxException {
        log.debug("REST request to update LaborUnion : {}", laborUnion);
        if (laborUnion.getId() == null) {
            return createLaborUnion(laborUnion);
        }
        LaborUnion result = laborUnionRepository.save(laborUnion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, laborUnion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /labor-unions : get all the laborUnions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of laborUnions in body
     */
    @GetMapping("/labor-unions")
    @Timed
    public ResponseEntity<List<LaborUnion>> getAllLaborUnions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LaborUnions");
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        Page<LaborUnion> page=null;
        if(user.get().getLogin().equals("system")){
            page = laborUnionRepository.findAll(pageable);
        }else {
            Tools.humpToline(pageable);
            page = laborUnionRepository.findByUserIsCurrentUser(user.get().getId(),pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/labor-unions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /labor-unions/:id : get the "id" laborUnion.
     *
     * @param id the id of the laborUnion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the laborUnion, or with status 404 (Not Found)
     */
    @GetMapping("/labor-unions/{id}")
    @Timed
    public ResponseEntity<LaborUnion> getLaborUnion(@PathVariable Long id) {
        log.debug("REST request to get LaborUnion : {}", id);
        LaborUnion laborUnion = laborUnionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(laborUnion));
    }

    /**
     * DELETE  /labor-unions/:id : delete the "id" laborUnion.
     *
     * @param id the id of the laborUnion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/labor-unions/{id}")
    @Timed
    public ResponseEntity<Void> deleteLaborUnion(@PathVariable Long id) {
        log.debug("REST request to delete LaborUnion : {}", id);
        laborUnionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
