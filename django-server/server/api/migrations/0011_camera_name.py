# Generated by Django 3.0.4 on 2020-06-07 17:15

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0010_fixedmenuoptionreview_review_text'),
    ]

    operations = [
        migrations.AddField(
            model_name='camera',
            name='name',
            field=models.CharField(default='nameless', max_length=50),
            preserve_default=False,
        ),
    ]