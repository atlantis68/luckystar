import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer} from '@angular/platform-browser';

@Pipe({ name: 'safe' })
export class SafePipe implements PipeTransform {
    constructor(private sanitizer: DomSanitizer) {}
    transform(url) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LuckystarSharedModule } from '../../shared';
import {

} from './';
import {MonitorComponent} from "./monitor.component";
import {MonitordRoute} from "./monitor.route";
import {CheckboxModule} from 'primeng/primeng';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { PlayerComponentComponent } from './player-component/player-component.component';
import {OnlineHighlightDirective} from "./online-highlight.directive";
const ENTITY_STATES = [
    ...MonitordRoute
];

@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        LuckystarSharedModule,
        CheckboxModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MonitorComponent,
        SafePipe,
        PlayerComponentComponent,
        OnlineHighlightDirective
    ],
    entryComponents: [

    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonitordModule {}


