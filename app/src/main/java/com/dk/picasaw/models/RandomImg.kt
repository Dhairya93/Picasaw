package com.dk.picasaw.models

import android.location.Location
import java.util.*


data class RandomImg(
    var id: String,
    var created_at: Date,
    var updated_at: Date,
    var promoted_at: Date,
    var width:Int,
    var height :Int,
    var color: String,
    var blur_hash: String,
    var description: String,
    var alt_description: String,
    var urls: Urls,
    var links: Links,
    var likes:Int,
    var liked_by_user:Boolean,
    var current_user_collections: ArrayList<Any>,
    var sponsorship: Any,
    var topic_submissions: TopicSubmissions,
    var user: User,
    var exif: Exif,
    var location: Location,
    var views:Int,
    var downloads:Int)

data class Urls(var raw: String,
                var full: String,
                var regular: String,
                var small: String,
                var thumb: String,
                var small_s3: String)

data class Exif (
    var make: String,
    var model: String,
    var name: String,
    var exposure_time: String,
    var aperture: String,
    var focal_length: String,
    var iso:Int )

data class Links (
    var self: String,
    var html: String,
    var download: String,
    var download_location: String,
    var photos: String,
    var likes: String,
    var portfolio: String,
    var following: String,
    var followers: String)

data class TopicSubmissions (
    var street_photography:StreetPhotography,
    var film:Film)
data class StreetPhotography (var status: String)
data class Film (var status: String)
data class User (
    var id: String,
    var updated_at: Date,
    var username: String,
    var name: String,
    var first_name: String,
    var last_name: String,
    var twitter_username: String,
    var portfolio_url: String,
    var bio: String,
    var location: String,
    var links: Links,
    var profile_image: ProfileImage,
    var instagram_username: String,
    var total_collections:Int,
    var total_likes:Int,
    var total_photos:Int,
    var accepted_tos:Boolean,
    var for_hire:Boolean,
    var social: Social)

data class Social (
    var instagram_username: String,
    var portfolio_url: String,
    var twitter_username: String,
    var paypal_email: Any
    )
data class ProfileImage (
    var small: String,
    var medium: String,
    var large: String
    )