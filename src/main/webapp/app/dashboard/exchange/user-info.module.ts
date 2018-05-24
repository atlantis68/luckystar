import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LuckystarSharedModule } from '../../shared';
import {
    UserInfoService,
    UserInfoPopupService,
    UserInfoComponent,
    UserInfoDetailComponent,
    UserInfoDialogComponent,
    UserInfoPopupComponent,
    UserInfoDeletePopupComponent,
    UserInfoDeleteDialogComponent,
    userInfoRoute,
    userInfoPopupRoute,
    UserInfoResolvePagingParams,
} from './';
import { ExchangeDialogComponent } from './exchange-dialog/exchange-dialog.component';
import {ExchangeService} from "./exchange-dialog/exchange.service";
import {ShowComponent} from "./show-dialog/show-dialog.component";


const ENTITY_STATES = [
    ...userInfoRoute,
    ...userInfoPopupRoute,
];

@NgModule({
    imports: [
        LuckystarSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserInfoComponent,
        UserInfoDetailComponent,
        UserInfoDialogComponent,
        UserInfoDeleteDialogComponent,
        UserInfoPopupComponent,
        UserInfoDeletePopupComponent,
        ExchangeDialogComponent,
        ShowComponent
    ],
    entryComponents: [
        UserInfoComponent,
        UserInfoDialogComponent,
        UserInfoPopupComponent,
        UserInfoDeleteDialogComponent,
        UserInfoDeletePopupComponent,
        ExchangeDialogComponent,
        ShowComponent
    ],
    providers: [
        UserInfoService,
        UserInfoPopupService,
        UserInfoResolvePagingParams,
        ExchangeService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExchangeModule {}
