# Create your views here.
import logging
from django.conf import settings
from app.models import Mail
from rest_framework import viewsets
from rest_framework.renderers import JSONRenderer
from app.serializers import MailSerializer
from django.core.exceptions import PermissionDenied

logger = logging.getLogger(__name__)

def send(to, cc, subject, body, attachments, keepRecord = True):
    #Set the recipient to the current user as a default
    if not to:
        raise "recipient not specified"  
                
    import pythoncom
    pythoncom.CoInitialize()
    import win32com.client
    sess=win32com.client.Dispatch("Notes.NotesSession")
    db = sess.getDatabase('','')
    db.openmail
    doc = db.createdocument
        
    mailContent = doc.CreateMIMEEntity
    mailSubject = mailContent.CreateHeader("Subject")
    mailSubject.SetHeaderVal(subject)
    mailTo = mailContent.CreateHeader("To")
    mailTo.SetHeaderVal(to)
    if cc:
        mailTo = mailContent.CreateHeader("Cc")
        mailTo.SetHeaderVal(cc)
    stream = sess.CreateStream
    stream.WriteText(body)
    mailContent.SetContentFromText(stream, "text/html;charset=UTF-8", 1729)
        
    #Notes attachments get made in RichText items...
    if attachments:
        rt = doc.createrichtextitem('Attachment')
        for f in attachments:
            rt.embedobject(1454,'',f)
    
    doc.SaveMessageOnSend = keepRecord 
    doc.Send(0)
        
class MailViewSet(viewsets.ModelViewSet):
    """
    ViewSet for list/create/read/update/delete of mail in JSON format only
    """
    model = Mail
    renderer_classes = [JSONRenderer]
    queryset = Mail.objects.all()
    serializer_class = MailSerializer
    return_403 = True
    
    def list(self, request, *args, **kwargs):
        if not request.user.is_staff:
            raise PermissionDenied
        return super(MailViewSet, self).list(request, *args, **kwargs)
        
    def create(self, request, *args, **kwargs):
        to = request.DATA.get('to', '')
        cc = request.DATA.get('cc', '')
        subject = request.DATA.get('subject', '')
        body = request.DATA.get('body', '') 
        attachments = None
        send(to, cc, subject, body, attachments, settings.KEEPRECORD)
        logger.debug("{0} {1} {2} {3}".format(to, cc, subject, body))
        return super(MailViewSet, self).create(request, *args, **kwargs)
    
    def retrieve(self, request, *args, **kwargs):
        if not request.user.is_staff:
            raise PermissionDenied
        mail = self.get_object()
        send(mail.to, mail.subject, mail.body, None)
        return super(MailViewSet, self).retrieve(request, *args, **kwargs)
    
    def update(self, request, *args, **kwargs):
        raise PermissionDenied
        
    def partial_update(self, request, *args, **kwargs):
        raise PermissionDenied
    
    def destroy(self, request, *args, **kwargs):
        raise PermissionDenied