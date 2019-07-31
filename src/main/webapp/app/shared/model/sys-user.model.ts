import { ISysGroup } from 'app/shared/model/sys-group.model';
import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';

export interface ISysUser {
  id?: number;
  userId?: number;
  status?: string;
  name?: string;
  sysPersonId?: number;
  groupIds?: ISysGroup[];
  enterpriseIds?: ISysEnterprise[];
}

export class SysUser implements ISysUser {
  constructor(
    public id?: number,
    public userId?: number,
    public status?: string,
    public name?: string,
    public sysPersonId?: number,
    public groupIds?: ISysGroup[],
    public enterpriseIds?: ISysEnterprise[]
  ) {}
}
