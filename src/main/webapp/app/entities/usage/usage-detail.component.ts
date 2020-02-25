import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsage } from 'app/shared/model/usage.model';

@Component({
  selector: 'jhi-usage-detail',
  templateUrl: './usage-detail.component.html'
})
export class UsageDetailComponent implements OnInit {
  usage: IUsage;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ usage }) => {
      this.usage = usage;
    });
  }

  previousState() {
    window.history.back();
  }
}
