import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {KpiByUserComponent} from './kpi-by-user.component';

@Injectable()
export class KpiByUserResolvePagingParams implements Resolve<any> {

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

export const kpiByUserRoute: Routes = [{
    path: 'kpi-by-user',
    component: KpiByUserComponent,
    resolve: {
        'pagingParams': KpiByUserResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'luckystarApp.ChickenInfoBoard.home.title'
    },
    canActivate: [UserRouteAccessService]
}];
