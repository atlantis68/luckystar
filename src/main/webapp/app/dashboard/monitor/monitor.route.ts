import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {MonitorComponent} from "./monitor.component";
import {PlayerComponentComponent} from "./player-component/player-component.component";


export const MonitordRoute: Routes = [{
    path: 'monitor',
    component: MonitorComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'luckystarApp.Monitor.home.title'
    },
    canActivate: [UserRouteAccessService]
}, {
    path: 'player',
    component: PlayerComponentComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'luckystarApp.Monitor.home.title'
    },
    canActivate: [UserRouteAccessService]
}];
