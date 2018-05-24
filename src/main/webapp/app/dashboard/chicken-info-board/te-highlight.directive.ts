import {AfterViewChecked, Directive, ElementRef, HostListener, Input, OnDestroy, OnInit} from '@angular/core';

@Directive({
    selector: '[myHighlight]'
})
export class TeHighlightDirective implements OnInit, OnDestroy,AfterViewChecked  {
    ngOnInit(): void {
        // if(this.min<this.max){
        //     this.highlight("green");
        // }
    }

    ngAfterViewChecked(): void {
        if(this.min>=this.max){
            this.highlight("green");
        }else {
            this.highlight(null);
        }
    }

    ngOnDestroy(): void {
    }

    constructor(private el: ElementRef) {

    }

    @Input() min: any;
    @Input() max: any;

    private highlight(color: string) {
        this.el.nativeElement.style.backgroundColor = color;
    }

}
