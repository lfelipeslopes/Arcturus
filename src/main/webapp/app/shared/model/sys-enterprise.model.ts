import { ISysGroup } from 'app/shared/model/sys-group.model';
import { ISysUser } from 'app/shared/model/sys-user.model';

export interface ISysEnterprise {
  id?: number;
  enterpriseId?: number;
  status?: string;
  enterprise?: string;
  alias?: string;
  sysLicenseId?: number;
  groupIds?: ISysGroup[];
  userIds?: ISysUser[];
}

export class SysEnterprise implements ISysEnterprise {
  constructor(
    public id?: number,
    public enterpriseId?: number,
    public status?: string,
    public enterprise?: string,
    public alias?: string,
    public sysLicenseId?: number,
    public groupIds?: ISysGroup[],
    public userIds?: ISysUser[]
  ) {}
}
