import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService} from 'ng-jhipster';

import {LaborUnionBoard} from './chicken-info-board.model';
import {ChickenInfoBoardService} from './chicken-info-board.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
import {LaborUnionService} from '../../entities/labor-union/labor-union.service';
import {NgbDatepickerI18n} from "@ng-bootstrap/ng-bootstrap";
import {CustomDatepickerI18n, I18n} from "../../shared/datepicker-i18n";

@Component({
    selector: 'jhi-labor-union',
    templateUrl: './chicken-info-board.component.html',
    providers: [I18n, {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class ChickenInfoBoardComponent implements OnInit, OnDestroy {

    currentAccount: any;
    laborUnionBoards: LaborUnionBoard[];
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
    recentTime: any;
    day: any;
    labor: any;
    searchCondition: string;
    laborUnions: any;
    minDate: any;
    maxDate: any;
    exportExcel:any;

    constructor(private chickenInfoBoardService: ChickenInfoBoardService,
                private laborUnionService: LaborUnionService,
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
        this.chickenInfoBoardService.query({
            query: {
                day: this.day.year + "-" + this.day.month + "-" + this.day.day,
                laborUnionId: this.labor,
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

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/labor-union', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'desc' : 'asc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        var date = new Date();
        this.day = {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
            day: date.getDate()
        }
        this.maxDate = Object.create(this.day);
        const minDate = Object.create(this.day);
        minDate.month -= 3;
        this.minDate = minDate;
        this.chickenInfoBoardService.recentTime().subscribe(
            (res: ResponseWrapper) => this.onSuccess1(res, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );


    }

    private onSuccess1(data, headers) {
        var data = data.json();
        this.recentTime = data.date;
        this.laborUnions = data.laborUnions;

        this.labor = this.laborUnions[0].id;
        this.exportExcel = this.laborUnions[0].exportExcel;
        this.loadAll();

    }

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LaborUnionBoard) {
        return item.id;
    }

    registerChangeInLaborUnions() {
        this.eventSubscriber = this.eventManager.subscribe('laborUnionListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'desc' : 'asc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        // this.links = this.parseLinks.parse(headers.get('link'));
        // this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.laborUnionBoards = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    statement(day?: string): void {
        this.day = day;
        this.loadAll();
    }

    statementLabor(labor?: number): void {
    	this.labor = labor;
        for (let item of this.laborUnions) {
            if(item.id == labor) {
            	this.exportExcel = item.exportExcel;
            }
        }
        this.loadAll();
    }

    getSumBeanByDay() {
        let sum: number = 0;
        for (let item of this.laborUnionBoards) {
            sum+=item.beanByDay;
        }
        return sum;
    }

    getSumBeanByMonth() {
        let sum: number = 0;
        for (let item of this.laborUnionBoards) {
            sum+=item.beanByMonth;
        }
        return sum;
    }

    export() {
        this.chickenInfoBoardService.export({
            query: {
                day: this.day.year + "-" + this.day.month + "-" + this.day.day,
                laborUnionId: this.labor,
                searchCondition: this.searchCondition
            },
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(res => {
            console.log('start download:',res);
            var url = window.URL.createObjectURL(res.data);
            var a = document.createElement('a');
            document.body.appendChild(a);
            a.setAttribute('style', 'display: none');
            a.href = url;
            a.download = res.filename;
            a.click();
            window.URL.revokeObjectURL(url);
            a.remove(); // remove the element
        }, error => {
            console.log('download error:', JSON.stringify(error));
        }, () => {
            console.log('Completed file download.')
        })
    }

}
