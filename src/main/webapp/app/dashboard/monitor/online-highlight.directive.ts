import {AfterViewChecked, Directive, ElementRef, HostListener, Input, OnDestroy, OnInit} from '@angular/core';

@Directive({
    selector: '[onlineHighlight]'
})
export class OnlineHighlightDirective implements OnInit, OnDestroy,AfterViewChecked  {
    ngOnInit(): void {
        // if(this.min<this.max){
        //     this.highlight("green");
        // }
    }

    ngAfterViewChecked(): void {
       if(this.online==1){
           this.highlight("green");
       }
    }

    ngOnDestroy(): void {
    }

    constructor(private el: ElementRef) {

    }

    @Input() online: any;

    private highlight(color: string) {
        this.el.nativeElement.style.backgroundColor = color;
    }

}
