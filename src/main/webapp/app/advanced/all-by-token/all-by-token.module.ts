import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LuckystarSharedModule } from '../../shared';
import { LuckystarAdminModule } from '../../admin/admin.module';
import {
    allByTokenRoute,
    AllByTokenComponent,
    AllByTokenService,
    AllByTokenResolvePagingParams
    // ChickenInfoBoardService,
    // ChickenInfoBoardComponent,
    // ChickenInfoBoardRoute,
    // ChickenInfoBoardResolvePagingParams
} from './';

const ENTITY_STATES = [
    ...allByTokenRoute
];

@NgModule({
    imports: [
        LuckystarSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AllByTokenComponent
    ],
    entryComponents: [
        AllByTokenComponent,
    ],
    providers: [
        AllByTokenService,
        AllByTokenResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LuckystarAllByTokenModule {}
