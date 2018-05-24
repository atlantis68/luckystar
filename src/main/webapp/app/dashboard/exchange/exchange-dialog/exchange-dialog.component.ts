import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {ExchangeService} from "./exchange.service";
import {UserInfo} from "../user-info.model";

@Component({
    selector: 'jhi-exchange-dialog',
    templateUrl: './exchange-dialog.component.html',
    styles: []
})
export class ExchangeDialogComponent implements OnInit {
    userInfo: UserInfo;
    public remainingBean: number;
    public exchangeNum: number;

    constructor(public activeModal: NgbActiveModal, public exchangeService: ExchangeService) {
    }

    ngOnInit() {
        this.exchangeService.getRemainingBean(this.userInfo.id).subscribe((res) => {
            this.remainingBean = res;
        })
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    exchange() {
        if(Number.isInteger(this.exchangeNum)&&this.exchangeNum>=5){
            this.exchangeService.exchangeRemainingBean(this.userInfo.id,this.exchangeNum*12500).subscribe((res) => {
                this.ngOnInit();
            })
        }else {
            alert("输入大于等于5的整数");
        }

    }
}
