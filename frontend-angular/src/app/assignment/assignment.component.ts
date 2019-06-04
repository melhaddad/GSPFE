import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SecurityService} from './../_service/security.service';
import {AuthService} from './../_service/auth.service';
import {NzMessageService, NzTabComponent} from 'ng-zorro-antd';
import {Item} from './../_model/item';
import {ItemService} from './../_service/item.service';
import {AssignmentService} from './../_service/assignment.service';
import {Assignment} from './../_model/assignment';
import {Component, OnInit} from '@angular/core';
import {User} from '../_model/user';
import {NamingService} from '../_service/naming.service';
import index from "@angular/cli/lib/cli";
import {Title} from "@angular/platform-browser";


@Component({
    selector: 'app-assignment',
    templateUrl: './assignment.component.html',
    styleUrls: ['./assignment.component.css']
})


export class AssignmentComponent implements OnInit {

    idx = 0;
    elements: Assignment[] = [];
    items: Item[] = [];
    nzTitle: any;

    loading = false;
    loadingItems = false;
    requesting = false;
    accepting = [];
    refusing = [];
    delivering = [];
    returning = [];
    taking = [];
    assignmentModal: Assignment = null;


    form: FormGroup = this.builder.group({
        message: this.builder.control(null, [Validators.required, Validators.minLength(15), Validators.maxLength(200)]),
        quantity: this.builder.control(null, [Validators.required, Validators.min(1), Validators.max(999)]),
    });
    acceptForm: FormGroup = this.builder.group({
        item: this.builder.control('', Validators.required),
        message: this.builder.control(null, [Validators.required, Validators.minLength(15), Validators.maxLength(200)]),
        quantity: this.builder.control('', [Validators.required, Validators.min(1), Validators.max(999)]),
    });

    constructor(
        private assignmentService: AssignmentService,
        private itemService: ItemService,
        private authService: AuthService,
        public security: SecurityService,
        public naming: NamingService,
        private messageService: NzMessageService,
        private builder: FormBuilder
    ) {
    }

    //Concerning modal input we define his property for displaying
    isVisible = false;
    //message input disabled
    value: string;


    showModal(i: Assignment): void {
        this.assignmentModal = {...i};
        this.isVisible = true;
    }

    handleCancel(): void {
        this.isVisible = false;
        this.acceptForm.reset();
    }

    url(nzTitle: any): string {
        switch (nzTitle) {
            case "Mes affectations":
                return '?own=1';
            case "Tous":
                return '';
            case "Demandes":
                return '?type=REQUESTED';
            case "À liverer":
                return '?type=ACCEPTED';
            case "À récupérer":
                return '?type=RETURNING';
            default:
                return '?own=1';
        }
    }

    get user(): User {
        return this.authService.user;
    }

    ngOnInit() {
        this.load();
        this.assignmentModal = new Assignment();
    }

    load() {
        this.loadAssignments({index: 0, tab: null});
        this.loadItems();
    }

    loadAssignments(data: {index: number, tab: NzTabComponent}) {
        if (!data) {
            return;
        }

        this.loading = true;
        if (data.tab) {
            this.nzTitle = data.tab.nzTitle;
        }
        this.assignmentService.findAll(this.url(this.nzTitle)).subscribe(elements => {
            this.elements = elements;
            this.loading = false;
        });
    }

    loadItems() {
        this.loadingItems = true;
        this.itemService.findAll().subscribe(elements => {
            this.items = elements;
            this.loadingItems = false;
        });
    }

    request() {
        this.requesting = true;
        const assignment: Assignment = {
            id: null,
            user: this.user,
            message: this.form.controls['message'].value,
            quantity: this.form.controls['quantity'].value,
            type: null
        };

        this.assignmentService.request(assignment).subscribe(() => {
            this.messageService.success('La demande d\'affectation a été effectuer avec succès.');
            this.reset();
            this.requesting = false;
            this.loadAssignments( {index:2, tab:null});
        }, request => {
            this.messageService.error(request.error.message);
            this.requesting = false;
        });
    }

    accept(element: Assignment) {
        this.accepting[element.id] = true;
        this.assignmentService.accept(element).subscribe(() => {
            this.messageService.success('L\'affectation a été accepté avec succès.');
            this.isVisible = false;
            this.accepting[element.id] = false;
            this.loadAssignments({index:2, tab:null});
        }, response => {
            this.messageService.error(response.error.message);
            this.accepting[element.id] = false;
        });
    }

    refuse(element: Assignment) {
        this.refusing[element.id] = true;
        this.assignmentService.refuse(element).subscribe(() => {
            this.messageService.success('L\'affectation a été refusé avec succès.');
            this.isVisible = false;
            this.refusing[element.id] = false;
            this.loadAssignments({index:2, tab:null});
        }, response => {
            this.messageService.error(response.error.message);
            this.refusing[element.id] = false;
        });
    }

    deliver(element: Assignment) {
        this.delivering[element.id] = true;
        this.assignmentService.deliver(element).subscribe(() => {
            this.messageService.success('L\'affectation a été livrer avec succès.');
            this.delivering[element.id] = false;
            this.loadAssignments({index:3, tab:null});
        }, response => {
            this.messageService.error(response.error.message);
            this.delivering[element.id] = false;
        });
    }

    return(element: Assignment) {
        this.returning[element.id] = true;
        this.assignmentService.return(element).subscribe(() => {
            this.messageService.success('L\'affectation a été retourné avec succès.');
            this.returning[element.id] = false;
            this.loadAssignments({index:4, tab:null});
        }, response => {
            this.messageService.error(response.error.message);
            this.returning[element.id] = false;
        });
    }

    take(element: Assignment) {
        this.taking[element.id] = true;
        this.assignmentService.take(element).subscribe(() => {
            this.messageService.success('L\'affectation a été récupérer avec succès.');
            this.taking[element.id] = false;
            this.loadAssignments({index:4, tab:null});
        }, response => {
            this.messageService.error(response.error.message);
            this.taking[element.id] = false;
        });
    }

    reset() {
        this.form.reset();
    }

    validateNumber(event) {
        const pattern = /[0-9]/;
        const inputChar = String.fromCharCode(event.charCode);
        if (event.keyCode !== 8 && !pattern.test(inputChar)) {
            event.preventDefault();
        }
    }

}
