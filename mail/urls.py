from django.conf.urls import patterns, include, url
from rest_framework import routers
from app import views
router = routers.SimpleRouter()
router.register(r'mail/api/mail', views.MailViewSet)

urlpatterns = patterns('',
    url(r'^', include(router.urls)),
)