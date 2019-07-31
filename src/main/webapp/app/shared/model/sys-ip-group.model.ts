import { ISysGroup } from 'app/shared/model/sys-group.model';

export interface ISysIpGroup {
  id?: number;
  ipGroupId?: number;
  groupIds?: ISysGroup[];
  sysIpGroupItemId?: number;
}

export class SysIpGroup implements ISysIpGroup {
  constructor(public id?: number, public ipGroupId?: number, public groupIds?: ISysGroup[], public sysIpGroupItemId?: number) {}
}
