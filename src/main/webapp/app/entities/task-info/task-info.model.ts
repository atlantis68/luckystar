import { BaseEntity } from './../../shared';

export class TaskInfo implements BaseEntity {
    constructor(
        public id?: number,
        public minTask?: number,
        public maxTask?: number,
        public curMonth?: number,
        public boundaryValue?: number,
        public workInfos?: BaseEntity[],
        public userInfo?: BaseEntity,
    ) {
    }
}
