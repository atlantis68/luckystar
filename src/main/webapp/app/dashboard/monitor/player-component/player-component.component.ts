import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs/Observable";

@Component({
    selector: 'jhi-player-component',
    templateUrl: './player-component.component.html',
    styles: []
})
export class PlayerComponentComponent implements OnInit {

    ids: string[];
	cols: string;
	sty: string;
	
    constructor(private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.ids = this.route.snapshot.queryParamMap.get('ids').split(",");
		this.cols = this.route.snapshot.queryParamMap.get('cols');
		if(this.cols == '2') {
			this.sty = 'col-lg-6 col-md-6 col-sm-6 col-sm-6';
		} else if(this.cols == '3') {
			this.sty = 'col-lg-4 col-md-4 col-sm-4 col-sm-4';
		} else if(this.cols == '4') {
			this.sty = 'col-lg-3 col-md-3 col-sm-3 col-sm-3';
		} else if(this.cols == '6') {
			this.sty = 'col-lg-2 col-md-2 col-sm-2 col-sm-2';
		} 
		console.log(this.sty)
    }

}
