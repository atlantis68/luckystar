import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { LuckystarSharedModule, UserRouteAccessService } from './shared';
import { LuckystarHomeModule } from './home/home.module';
import { LuckystarAdminModule } from './admin/admin.module';
import { LuckystarAccountModule } from './account/account.module';
import { LuckystarEntityModule } from './entities/entity.module';
import { LuckystarDashboardModule } from './dashboard/dashboard.module';
import { LuckystarAdvancedModule } from './advanced/advanced.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        NgbModule.forRoot(),
        LuckystarSharedModule,
        LuckystarHomeModule,
        LuckystarAdminModule,
        LuckystarAccountModule,
        LuckystarEntityModule,
        LuckystarDashboardModule,
        LuckystarAdvancedModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class LuckystarAppModule {}
