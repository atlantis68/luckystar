import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LuckystarSharedModule } from '../../shared';
import { LuckystarAdminModule } from '../../admin/admin.module';
import {
    ChickenInfoBoardService,
    ChickenInfoBoardComponent,
    ChickenInfoBoardRoute,
    ChickenInfoBoardResolvePagingParams,
    TeHighlightDirective
} from './';

const ENTITY_STATES = [
    ...ChickenInfoBoardRoute
];

@NgModule({
    imports: [
        // ChickenInfoBoardService,
        // ChickenInfoBoardComponent,
        LuckystarSharedModule,
        // LuckystarAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        // ChickenInfoBoardService,
        ChickenInfoBoardComponent,
        TeHighlightDirective
    ],
    entryComponents: [
        // ChickenInfoBoardService,
        ChickenInfoBoardComponent
    ],
    providers: [
        // ChickenInfoBoardService,
        // ChickenInfoBoardComponent
        ChickenInfoBoardResolvePagingParams,
        ChickenInfoBoardService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LuckystarChickenInfoBoardModule {}
