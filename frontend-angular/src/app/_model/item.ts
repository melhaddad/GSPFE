import { Category } from './category';
export class Item {
  id: number;
  name: string;
  description: string;
  quantity: number;
  info: number;
  warning: number;
  error: number;
  category: Category;
}
