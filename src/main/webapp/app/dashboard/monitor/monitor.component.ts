import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiPaginationUtil, JhiParseLinks} from 'ng-jhipster';

import {Principal} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
import {NgbDatepickerI18n} from "@ng-bootstrap/ng-bootstrap";
import {CustomDatepickerI18n, I18n} from "../../shared/datepicker-i18n";
import {UserInfoService} from "../../entities/user-info/user-info.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Http, Response} from '@angular/http';

@Component({
    selector: 'jhi-monitor',
    templateUrl: './monitor.component.html',
    providers: [I18n, {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class MonitorComponent implements OnInit, OnDestroy {
    selectedValues: string[] = [];
    items: any;
    cols: number;
    isSelected: boolean = false;

    constructor(private http: Http,
                private sanitizer: DomSanitizer,
                private userInfoService: UserInfoService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig) {

    }


    ngOnInit() {
        this.http.get("api/monitor-infos").map((res: Response) => {
            return res.json();
        }).subscribe(
            (res: any) => {
                this.items = res
            });
        // .map((res: Response) => this.onSuccess(res.json, res.headers));


        // this.userInfoService.query({
        //     page: 0,
        //     size: 300}).subscribe(
        //     (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
        //     (res: ResponseWrapper) => this.onError(res.json)
        // );
        this.cols = 2
    }


    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    private onSuccess(data, headers) {
        this.items = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    newpage() {
        if (this.selectedValues.length == 0) {
            alert("最少选着一项");
        } else {
            window.open('/#/player?cols=' + this.cols + '&ids=' + this.selectedValues, '_blank');
        }
    }
    
    refresh() {
        this.http.get("api/monitor-infos").map((res: Response) => {
            return res.json();
        }).subscribe(
            (res: any) => {
                this.items = res
            });
    }   
    
    selecteAll() {
    	this.selectedValues = [];
		if(!this.isSelected) {
	    	for (let x in this.items) {
	    		this.selectedValues.push(this.items[x].id.toString());
	    	}
	    	this.isSelected = true; 
		} else {
			this.isSelected = false;
		}
    }     
}
