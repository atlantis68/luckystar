import {Component, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'jhi-show-dialog',
    templateUrl: './show-dialog.component.html',
    styles: []
})
export class ShowComponent implements OnInit {

    constructor(public activeModal: NgbActiveModal, private modalService: NgbModal) {
    }

    ngOnInit() {

    }

    clear() {
        this.activeModal.dismiss('cancel');
    }
}
