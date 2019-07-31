import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';

export interface ISysIpGroupItem {
  id?: number;
  ipGroupItemId?: number;
  initialIp?: string;
  finalIp?: string;
  sysIpGroups?: ISysIpGroup[];
}

export class SysIpGroupItem implements ISysIpGroupItem {
  constructor(
    public id?: number,
    public ipGroupItemId?: number,
    public initialIp?: string,
    public finalIp?: string,
    public sysIpGroups?: ISysIpGroup[]
  ) {}
}
