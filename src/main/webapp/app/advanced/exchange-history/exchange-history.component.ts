import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService} from 'ng-jhipster';


import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';

import {ExchangeHistoryService} from './exchange-history.service'
import {CustomDatepickerI18n, I18n} from "../../shared/datepicker-i18n";
import {NgbDatepickerI18n} from "@ng-bootstrap/ng-bootstrap";


@Component({
    selector: 'jhi-exchange-history',
    templateUrl: './exchange-history.component.html',
    providers: [I18n, {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class ExchangeHistoryComponent implements OnInit, OnDestroy {
    currentAccount: any;
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
    laborUnionBoards: any
    labor: any
    data: any
    uniqueDate: any

    laborUnions: any

    regDate: any
    day: any;
    searchCondition: any
    minDate: any;
    maxDate: any;
    exportExcel:any;
    sums:any;

    constructor(private exchangeHistoryService: ExchangeHistoryService,
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
        this.exchangeHistoryService.query({
            query: {
                day: this.day,
                laborUnionId: this.labor,
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

        this.exchangeHistoryService.recentTime().subscribe(
            (res: ResponseWrapper) => this.onSuccess1(res, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

    ngOnDestroy(): void {
    }


    private onSuccess1(data, headers) {
        var data = data.json();
        this.laborUnions = data.laborUnions;

        this.labor = this.laborUnions[0].id;
        this.exportExcel = this.laborUnions[0].exportExcel;
        this.loadAll();

    }

    private onSuccess(data, headers) {
        // this.links = this.parseLinks.parse(headers.get('link'));
        // this.totalItems = headers.get('X-Total-Count');

		this.data = data;

        // this.totalItems=uniqueId.length;
        this.queryCount = this.totalItems;
        
        for (let item of this.laborUnions) {
            if(item.id == this.labor) {
            	this.exportExcel = item.exportExcel;
            }
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

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {

        if (this.sort()[0].indexOf(",") > -1) {
            let str: string[] = this.sort()[0].split(",");
            if (str[0].match(/^\d{4}-\d{2}-\d{2}$/ig)) {
                if(str[1]=="asc") {
                    this.data = this.data.sort((a, b) => {
                        return a.date[str[0]] > b.date[str[0]] ? 1 : -1;
                    });
                }else {
                    this.data = this.data.sort((a, b) => {
                        return a.date[str[0]] < b.date[str[0]] ? 1 : -1;
                    });
                }
                return;
            }else if(str[0]=="total"){
                if(str[1]=="asc") {
                    this.data = this.data.sort((a, b) => {
                        return a.total > b.total ? 1 : -1;
                    });
                }else {
                    this.data = this.data.sort((a, b) => {
                        return a.total < b.total ? 1 : -1;
                    });
                }
                return;
            }
        }
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

    statementLabor(labor?: string): void {
        this.labor = labor;
        this.loadAll();
    }

    getSum(list?: number[]) {
        let sum: number = 0;
        for (let it in list) {
            sum += list[it];
        }
        return sum;
    }

    bind(ev: any) {
        console.log(ev)
    }
    
   export() {
        this.exchangeHistoryService.export({
            query: {
                day: this.day,
                laborUnionId: this.labor,
                date: this.regDate.year + "-" + this.regDate.month + "-" + this.regDate.day,
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

