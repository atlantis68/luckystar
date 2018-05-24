import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LuckystarSharedModule } from '../shared';
import { LuckystarChickenInfoBoardModule } from './chicken-info-board/chicken-info-board.module';

import {LuckystarWorkTimeBoardModule} from "./work-time-board/work-time-board.module";
import {MonitordModule} from "./monitor/monitor.module";
import {ExchangeModule} from "./exchange/user-info.module";

@NgModule({
    imports: [
        LuckystarSharedModule,
        LuckystarChickenInfoBoardModule,
        LuckystarWorkTimeBoardModule,
        MonitordModule,
        ExchangeModule
        // RouterModule.forRoot(dashboardState, { useHash: true })
    ],
    declarations: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LuckystarDashboardModule {}
