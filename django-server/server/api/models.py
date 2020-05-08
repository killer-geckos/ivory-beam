from django.core.validators import (MaxValueValidator, MinValueValidator,
                                    URLValidator)
from django.db import models
from django.db.models import F


class Cafeteria(models.Model):
    name = models.CharField(max_length=128)
    description = models.CharField(max_length=256)
    sub_description = models.CharField(max_length=512)
    latitude = models.FloatField(validators=[MinValueValidator(-90.0), MaxValueValidator(90.0)])
    longitude = models.FloatField(validators=[MinValueValidator(-180.0), MaxValueValidator(180.0)])
    capacity = models.IntegerField(validators=[MinValueValidator(1)])
    logo_url = models.CharField(max_length=2048, validators=[URLValidator])
    address = models.CharField(max_length=256)
    opened_from = models.TimeField()
    opened_to = models.TimeField()

    def __str__(self):
        return self.name


class FixedMenuOption(models.Model):
    name = models.CharField(max_length=128)
    price = models.FloatField(validators=[MinValueValidator(0.0)])
    cafeteria = models.ForeignKey(Cafeteria, on_delete=models.CASCADE, related_name='fixed_menu_options')
    photo_url = models.CharField(max_length=2048, validators=[URLValidator])
    avg_review_stars = models.FloatField(validators=[MinValueValidator(0), MaxValueValidator(5)],
                                         default=0, editable=False)

    def __str__(self):
        return self.name


class MenuOptionTag(models.Model):
    name = models.CharField(max_length=32, editable=False)
    option = models.ManyToManyField(FixedMenuOption, related_name='menu_option_tags')

    def __str__(self):
        return self.name


class FixedMenuOptionReview(models.Model):
    option = models.ForeignKey(FixedMenuOption, on_delete=models.CASCADE, related_name='fixed_menu_option_reviews')
    stars = models.IntegerField(validators=[MinValueValidator(0), MaxValueValidator(5)])
    author_nick = models.CharField(max_length=64)
    review_time = models.DateTimeField()

    def __str__(self):
        return f'{self.option} by {self.author_nick} with {self.stars}'

    def save(self, *args, **kwargs):
        curr_reviews = FixedMenuOptionReview.objects.count()
        if not self.pk:
            FixedMenuOption.objects.filter(pk=self.option.pk).update(avg_review_stars=self.calculate_new_avg_review(F('avg_review_stars'), curr_reviews))
        super().save(*args, **kwargs)

    def calculate_new_avg_review(self, old_review_stars, number_of_reviews):
        current_sum = old_review_stars * number_of_reviews + self.stars
        return current_sum / (number_of_reviews + 1)
