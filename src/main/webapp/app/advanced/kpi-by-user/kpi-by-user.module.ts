import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LuckystarSharedModule } from '../../shared';
import { LuckystarAdminModule } from '../../admin/admin.module';
import {
    kpiByUserRoute,
    KpiByUserComponent,
    KpiByUserService,
    KpiByUserResolvePagingParams
    // ChickenInfoBoardService,
    // ChickenInfoBoardComponent,
    // ChickenInfoBoardRoute,
    // ChickenInfoBoardResolvePagingParams
} from './';

const ENTITY_STATES = [
    ...kpiByUserRoute
];

@NgModule({
    imports: [
        LuckystarSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KpiByUserComponent
    ],
    entryComponents: [
        KpiByUserComponent,
    ],
    providers: [
        KpiByUserService,
        KpiByUserResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LuckystarKpiByUserModule {}
