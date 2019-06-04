import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UploadFile, NzMessageService } from 'ng-zorro-antd';
import { NamingService } from '../_service/naming.service';
import { ProfileService } from '../_service/profile.service';
import { User } from '../_model/user';

@Component({
  selector: 'app-change-image-form',
  templateUrl: './change-image-form.component.html',
  styleUrls: ['./change-image-form.component.css']
})
export class ChangeImageFormComponent implements OnInit {
  @Output()
  load: EventEmitter<any> = new EventEmitter();

  uploading = false;
  fileList: UploadFile[] = [];

  upload_id: any;

  constructor(
    private service: ProfileService,
    public naming: NamingService,
    private message: NzMessageService
  ) { }

  get user(): User {
    return this.service.user;
  }

  ngOnInit() { }

  beforeUpload = (file: File) => {
    const isJPG = file.type === 'image/jpeg';
    if (!isJPG) {
      this.message.error('Vous ne pouvez télécharger que des image JPG!');
    }
    const isLt1M = file.size / 1024 / 1024 <= 1;
    if (!isLt1M) {
      this.message.error('La taille de l\'image doit être inférieure à 1 Mo!');
    }
    return isJPG && isLt1M;
  }

  getBase64(img: File, callback: (img: {}) => void): void {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
  }

  handleChange(info: { file: UploadFile }): void {
    if (info.file.status === 'uploading') {
      if (!this.upload_id) {
        this.upload_id = this.message.loading('téléchargement de l\'image...', { nzDuration: 0 }).messageId;
      }
      return;
    }
    if (info.file.status === 'done') {
      this.getBase64(info.file.originFileObj, (img: string) => {
        // this.message.remove(this.upload_id);
        this.message.remove();
        this.upload_id = false;
        this.message.success('L\'image a été changer');
        this.load.emit(null);
      });
    }
  }

}
