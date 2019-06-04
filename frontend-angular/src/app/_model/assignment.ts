import { Item } from './item';
import { User } from './user';
export class Assignment {
  id: number;
  user: User;
  item?: Item;
  message: string;
  quantity: number;
  type: string;
}
