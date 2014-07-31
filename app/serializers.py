from rest_framework import serializers
from app.models import Mail

class MailSerializer(serializers.ModelSerializer):
    class Meta:
        model = Mail
        fields = ('id', 'to', 'cc', 'subject', 'body')