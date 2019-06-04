import { HistoryType } from './history-type.enum';
export class History<T> {
  id: number;
  current: T;
  previous: T;
  user = 'Système';
  type: HistoryType;
  date: string;

  get action(): string {
    switch (this.type) {
      case HistoryType.CREATED: return 'Insertion';
      case HistoryType.UPDATED: return 'Modification';
      case HistoryType.DELETED: return 'Supression';
    }
  }

  get color(): string {
    switch (this.type) {
      case HistoryType.CREATED: return 'green';
      case HistoryType.UPDATED: return 'orange';
      case HistoryType.DELETED: return 'red';
    }
  }
}
