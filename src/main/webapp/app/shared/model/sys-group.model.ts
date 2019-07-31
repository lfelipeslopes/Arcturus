import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';
import { ISysAccess } from 'app/shared/model/sys-access.model';
import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';
import { ISysUser } from 'app/shared/model/sys-user.model';

export interface ISysGroup {
  id?: number;
  groupId?: number;
  status?: string;
  group?: string;
  ipGroupIds?: ISysIpGroup[];
  accessIds?: ISysAccess[];
  enterpriseIds?: ISysEnterprise[];
  userIds?: ISysUser[];
}

export class SysGroup implements ISysGroup {
  constructor(
    public id?: number,
    public groupId?: number,
    public status?: string,
    public group?: string,
    public ipGroupIds?: ISysIpGroup[],
    public accessIds?: ISysAccess[],
    public enterpriseIds?: ISysEnterprise[],
    public userIds?: ISysUser[]
  ) {}
}
