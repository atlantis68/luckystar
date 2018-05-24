import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TaskInfo } from './task-info.model';
import { TaskInfoPopupService } from './task-info-popup.service';
import { TaskInfoService } from './task-info.service';
import { UserInfo, UserInfoService } from '../user-info';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-task-info-dialog',
    templateUrl: './task-info-dialog.component.html'
})
export class TaskInfoDialogComponent implements OnInit {

    taskInfo: TaskInfo;
    isSaving: boolean;

    userinfos: UserInfo[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private taskInfoService: TaskInfoService,
        private userInfoService: UserInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userInfoService.query({
            page: 0,
            size: 300})
            .subscribe((res: ResponseWrapper) => { this.userinfos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.taskInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.taskInfoService.update(this.taskInfo));
        } else {
            this.subscribeToSaveResponse(
                this.taskInfoService.create(this.taskInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<TaskInfo>) {
        result.subscribe((res: TaskInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TaskInfo) {
        this.eventManager.broadcast({ name: 'taskInfoListModification', content: 'OK'});
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

    trackUserInfoById(index: number, item: UserInfo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-task-info-popup',
    template: ''
})
export class TaskInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taskInfoPopupService: TaskInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.taskInfoPopupService
                    .open(TaskInfoDialogComponent as Component, params['id']);
            } else {
                this.taskInfoPopupService
                    .open(TaskInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
