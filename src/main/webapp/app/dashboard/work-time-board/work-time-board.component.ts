import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService} from 'ng-jhipster';


import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';

import {WorkTimeBoardService} from './work-time-board.service'
import {CustomDatepickerI18n, I18n} from "../../shared/datepicker-i18n";
import {NgbDatepickerI18n} from "@ng-bootstrap/ng-bootstrap";


@Component({
    selector: 'jhi-work-time-board',
    templateUrl: './work-time-board.component.html',
    providers: [I18n, {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class WorkTimeBoardComponent implements OnInit, OnDestroy {
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

    constructor(private workTimeBoardService: WorkTimeBoardService,
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
        this.workTimeBoardService.query({
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

        this.workTimeBoardService.recentTime().subscribe(
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


        var map = {};
        var tmp = {};
        var uniqueId: number[] = [];
        for (let x in data) {
            if (!map[data[x].starId]) {
                map[data[x].starId] = {data: data[x], date: {}};
                uniqueId.push(data[x].starId)
            }
            map[data[x].starId].date[data[x].curDay] = data[x].workTime
            tmp[data[x].curDay] = true;
        }
        this.data = [];
        for (let x in uniqueId) {
            this.data.push(map[uniqueId[x]])
        }


        var uniqueDate: string[] = [];
        for (let x in tmp) {
            uniqueDate.push(x);
        }
        this.uniqueDate = uniqueDate.sort((a, b) => {
            return a < b ? 1 : -1;
        });

        for (let item in this.data) {
            let sum: number = 0;
            for (let ix in uniqueDate) {
            	if (!this.data[item].date[uniqueDate[ix]]) {
            		this.data[item].date[uniqueDate[ix]] = 0;
            	}
                sum += this.data[item].date[uniqueDate[ix]];
            }
            this.data[item].total = sum;
        }
        // this.totalItems=uniqueId.length;
        this.queryCount = this.totalItems;
        
        for (let item of this.laborUnions) {
            if(item.id == this.labor) {
            	this.exportExcel = item.exportExcel;
            }
        }
    }

    private onError(error) {

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
        this.workTimeBoardService.export({
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

