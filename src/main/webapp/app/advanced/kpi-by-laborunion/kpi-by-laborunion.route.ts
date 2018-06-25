import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {KpiByLaborUnionComponent} from './kpi-by-laborunion.component';

@Injectable()
export class KpiByLaborUnionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const kpiByLaborUnionRoute: Routes = [{
    path: 'kpi-by-laborunion',
    component: KpiByLaborUnionComponent,
    resolve: {
        'pagingParams': KpiByLaborUnionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'luckystarApp.ChickenInfoBoard.home.title'
    },
    canActivate: [UserRouteAccessService]
}];
