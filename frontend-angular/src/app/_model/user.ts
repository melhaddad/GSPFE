import { Authority } from './authority';
import { Role } from './role';
import { Privilege } from './privilege';
export class User {
  id: number;
  lastName: string;
  firstName: string;
  address: string;
  function: string;
  email: string;
  password = 'ignored password';
  image = 'default';
  roles?: Role[];
  privileges?: Privilege[];
  authorities?: Authority[];
}
