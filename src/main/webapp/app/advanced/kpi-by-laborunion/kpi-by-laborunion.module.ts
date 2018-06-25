import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LuckystarSharedModule } from '../../shared';
import { LuckystarAdminModule } from '../../admin/admin.module';
import {
    kpiByLaborUnionRoute,
    KpiByLaborUnionComponent,
    KpiByLaborUnionService,
    KpiByLaborUnionResolvePagingParams
    // ChickenInfoBoardService,
    // ChickenInfoBoardComponent,
    // ChickenInfoBoardRoute,
    // ChickenInfoBoardResolvePagingParams
} from './';

const ENTITY_STATES = [
    ...kpiByLaborUnionRoute
];

@NgModule({
    imports: [
        LuckystarSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KpiByLaborUnionComponent
    ],
    entryComponents: [
        KpiByLaborUnionComponent,
    ],
    providers: [
        KpiByLaborUnionService,
        KpiByLaborUnionResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LuckystarKpiByLaborUnionModule {}
