# Generated by Django 3.0.6 on 2020-05-24 15:53

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0004_auto_20200524_1750'),
    ]

    operations = [
        migrations.AlterField(
            model_name='camera',
            name='state',
            field=models.IntegerField(choices=[(0, 'online'), (1, 'offline'), (2, 'lost connection')]),
        ),
    ]
