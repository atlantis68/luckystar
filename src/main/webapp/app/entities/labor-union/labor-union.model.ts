import { BaseEntity, User } from './../../shared';

const enum State {
    'ON',
    'OFF'
}

export class LaborUnion implements BaseEntity {
    constructor(
        public id?: number,
        public lId?: number,
        public name?: string,
        public regDate?: any,
        public state?: State,
        public users?: User[],
    ) {
    }
}
