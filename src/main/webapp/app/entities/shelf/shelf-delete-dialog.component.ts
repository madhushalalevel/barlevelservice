import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShelf } from 'app/shared/model/shelf.model';
import { ShelfService } from './shelf.service';

@Component({
  templateUrl: './shelf-delete-dialog.component.html'
})
export class ShelfDeleteDialogComponent {
  shelf?: IShelf;

  constructor(protected shelfService: ShelfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shelfService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shelfListModification');
      this.activeModal.close();
    });
  }
}
