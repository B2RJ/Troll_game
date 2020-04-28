# !/usr/bin/python
# -*-coding:utf-8 -*

class CoupInvalide(Exception):
    def __init__(self, message = "Coup invalide"):
        Exception.__init__(self,message)