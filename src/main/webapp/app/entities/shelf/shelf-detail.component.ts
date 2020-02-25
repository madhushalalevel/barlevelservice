import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShelf } from 'app/shared/model/shelf.model';

@Component({
  selector: 'jhi-shelf-detail',
  templateUrl: './shelf-detail.component.html'
})
export class ShelfDetailComponent implements OnInit {
  shelf: IShelf;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shelf }) => {
      this.shelf = shelf;
    });
  }

  previousState() {
    window.history.back();
  }
}
