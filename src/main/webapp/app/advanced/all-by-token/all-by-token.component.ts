import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService} from 'ng-jhipster';


import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';

import {AllByTokenService} from './all-by-token.service'
import {CustomDatepickerI18n, I18n} from "../../shared/datepicker-i18n";
import {NgbDatepickerI18n} from "@ng-bootstrap/ng-bootstrap";


@Component({
    selector: 'jhi-all-by-token',
    templateUrl: './all-by-token.component.html',
    providers: [I18n, {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class AllByTokenComponent implements OnInit, OnDestroy {
    currentAccount: any;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    data: any
    uniqueDate: any

    regDate: any
    day: any;
    searchCondition: any
    minDate: any;
    maxDate: any;
    sums:any;
    allBean:number = 0;
    allTime:number = 0;

    constructor(private allByTokenService: AllByTokenService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
    	document.getElementById('base').style.display='none';
        this.allByTokenService.query({
            query: {
                day: this.day,
                date: this.regDate.year + "-" + this.regDate.month + "-" + this.regDate.day,
                searchCondition: this.searchCondition
            },
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit(): void {
        this.day = 0;
        var date = new Date();
        this.regDate = {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
            day: date.getDate()
        }

        this.maxDate = Object.create(this.regDate);
        const minDate = Object.create(this.regDate);
        minDate.month -= 3;
        this.minDate = minDate;

		this.loadAll();

    }

    ngOnDestroy(): void {
    }

    private onSuccess(data, headers) {
        // this.links = this.parseLinks.parse(headers.get('link'));
        // this.totalItems = headers.get('X-Total-Count');

		this.allBean = 0;
		this.allTime = 0;
		this.data = data;
		for (let x in data) {
			this.allBean += parseInt(data[x].judgeTimeByMonth ? data[x].judgeTimeByMonth : '0');
			this.allTime += data[x].workTime ? data[x].workTime : 0;
		}

        document.getElementById('base').style.display='block';
    }

    private onError(error) {
		document.getElementById('base').style.display='block';
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'desc' : 'asc')];
        // if (this.predicate !== 'id') {
        //     result.push('id');
        // }
        return result;
    }

}

