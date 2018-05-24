import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal, NgbDatepickerI18n} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {UserInfo} from './user-info.model';
import {UserInfoPopupService} from './user-info-popup.service';
import {UserInfoService} from './user-info.service';
import {LaborUnion, LaborUnionService} from '../../entities/labor-union';
import {ResponseWrapper} from '../../shared';
import {CustomDatepickerI18n, I18n} from "../../shared/datepicker-i18n";
import {ExchangeDialogComponent} from "./exchange-dialog/exchange-dialog.component";
import {ShowComponent} from "./show-dialog/show-dialog.component";

@Component({
    selector: 'jhi-user-info-dialog',
    templateUrl: './user-info-dialog.component.html',
    providers: [I18n, {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class UserInfoDialogComponent implements OnInit {

    userInfo: UserInfo;
    isSaving: boolean;

    laborunions: LaborUnion[];
    regDateDp: any;
    lastMaintainDp: any;

    constructor(public activeModal: NgbActiveModal,
                private alertService: JhiAlertService,
                private userInfoService: UserInfoService,
                private laborUnionService: LaborUnionService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.laborUnionService.query()
            .subscribe((res: ResponseWrapper) => {
                this.laborunions = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userInfoService.update(this.userInfo));
        } else {
            this.subscribeToSaveResponse(
                this.userInfoService.create(this.userInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserInfo>) {
        result.subscribe((res: UserInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserInfo) {
        this.eventManager.broadcast({name: 'userInfoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackLaborUnionById(index: number, item: LaborUnion) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-info-popup',
    template: ''
})
export class UserInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private userInfoPopupService: UserInfoPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {

            if (this.route.snapshot.routeConfig.path.indexOf("exchange") > -1) {
                this.userInfoPopupService
                    .open(ExchangeDialogComponent as Component, params['id']);
            }
            else if (this.route.snapshot.routeConfig.path.indexOf("show") > -1) {
                this.userInfoPopupService
                    .open(ShowComponent as Component, params['id']);
            }
            else {
                if (params['id']) {
                    this.userInfoPopupService
                        .open(UserInfoDialogComponent as Component, params['id']);
                } else {
                    this.userInfoPopupService
                        .open(UserInfoDialogComponent as Component);
                }
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
