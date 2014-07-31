import os

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

DEBUG = False

KEEPRECORD = False

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',  # Add 'postgresql_psycopg2', 'mysql', 'sqlite3' or 'oracle'.
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),  # Or path to database file if using sqlite3.
    }
}

ALLOWED_HOSTS = ['localhost', '127.0.0.1']

LOGGING = {
    'version': 1,
    'disable_existing_loggers': False,
    'handlers': {
        'file': {
            'level': 'DEBUG',
            'class': 'logging.FileHandler',
            'filename': os.path.join(BASE_DIR, 'stdout.log'),
        }
    },
           
    'loggers': {
        'django.request': {
            'handlers': ['file'],
            'level': 'DEBUG',
            'propagate': True,
        },
        'app': {
            'handlers': ['file'],
            'level': 'INFO',
            'propagate': True,
        },
    }
}
