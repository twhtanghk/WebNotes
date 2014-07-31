from django.db import models

# Create your models here.
class Mail(models.Model):
    subject = models.CharField(max_length=1024, null=False, blank=False)
    body = models.CharField(max_length=10240, null=False, blank=False)
    to = models.CharField(max_length=1024, null=False, blank=False)
    cc = models.CharField(max_length=1024, null=True, blank=True)