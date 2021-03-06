import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { UserInfo } from './user-info.model';
import { UserInfoService } from './user-info.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {LaborUnionService} from "../labor-union/labor-union.service";

@Component({
    selector: 'jhi-user-info',
    templateUrl: './user-info.component.html'
})
export class UserInfoComponent implements OnInit, OnDestroy {

currentAccount: any;
    userInfos: UserInfo[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    maxMember: number;
    searchCondition: any;
    
    constructor(
        private userInfoService: UserInfoService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
    private laborUnionService: LaborUnionService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
    	var query = {};
    	query['searchCondition']=this.searchCondition;
        this.userInfoService.query({
        	query: query, 
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.laborUnionService.query()
            .subscribe((res: ResponseWrapper) => {
                let total: number = 0;
                for (let item of res.json) {
                    total += item.maxMember;
                }
                this.maxMember = total;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/user-info'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ?  'desc' : 'asc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/user-info', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ?  'desc' : 'asc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserInfo) {
        return item.id;
    }
    registerChangeInUserInfos() {
        this.eventSubscriber = this.eventManager.subscribe('userInfoListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ?  'desc' : 'asc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
    	if(this.searchCondition) {
	        this.totalItems = 0;
    	} else {
    		this.totalItems = headers.get('X-Total-Count');
    	}
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.userInfos = data;
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
