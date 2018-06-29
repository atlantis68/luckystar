import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LuckystarSharedModule } from '../../shared';
import { LuckystarAdminModule } from '../../admin/admin.module';
import {
    exchangeHistoryRoute,
    ExchangeHistoryComponent,
    ExchangeHistoryService,
    ExchangeHistoryResolvePagingParams
    // ChickenInfoBoardService,
    // ChickenInfoBoardComponent,
    // ChickenInfoBoardRoute,
    // ChickenInfoBoardResolvePagingParams
} from './';

const ENTITY_STATES = [
    ...exchangeHistoryRoute
];

@NgModule({
    imports: [
        LuckystarSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExchangeHistoryComponent
    ],
    entryComponents: [
        ExchangeHistoryComponent,
    ],
    providers: [
        ExchangeHistoryService,
        ExchangeHistoryResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LuckystarExchangeHistoryModule {}
