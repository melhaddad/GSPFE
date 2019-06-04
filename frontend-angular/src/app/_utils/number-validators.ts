import { ValidatorFn, FormControl, AbstractControl } from '@angular/forms';

export class NumberValidators {

  static isNumberCheck(): ValidatorFn {
    return (c: AbstractControl): { [key: string]: boolean } | null => {
      console.log('qsdf');
      if (c.value !== undefined && (isNaN(c.value))) {
        return { 'value': true };
      }

      return null;
    };
  }
}
