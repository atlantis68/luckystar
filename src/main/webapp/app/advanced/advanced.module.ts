import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LuckystarSharedModule } from '../shared';
import { LuckystarKpiByUserModule } from './kpi-by-user/kpi-by-user.module';

@NgModule({
    imports: [
        LuckystarSharedModule,
        LuckystarKpiByUserModule
        // RouterModule.forRoot(dashboardState, { useHash: true })
    ],
    declarations: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LuckystarAdvancedModule {}
