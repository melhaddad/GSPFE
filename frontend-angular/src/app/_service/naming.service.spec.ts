import { TestBed, inject } from '@angular/core/testing';

import { NamingService } from './naming.service';

describe('NamingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NamingService]
    });
  });

  it('should be created', inject([NamingService], (service: NamingService) => {
    expect(service).toBeTruthy();
  }));
});
