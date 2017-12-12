from threading import Thread
from queue import Queue
from abc import ABCMeta, abstractmethod


class Job(Thread):

    __metaclass__ = ABCMeta

    def __init__(self):
        super(Job, self).__init__()
        self._command_queue = Queue()
        self._job_state = 0x0000

    @property
    def command_queue(self):
        return self._command_queue

    @command_queue.setter
    def command_queue(self, command_queue):
        self._command_queue = command_queue

    @abstractmethod
    def run(self):
        pass

    @abstractmethod
    def on_action(self, command):
        pass

    def add_command(self, command):
        self._command_queue.put(command)

    def _receive_command(self):
        if self._command_queue.empty():
            return None
        else:
            return self._command_queue.get()

    def get_job_state_flag(self, flag):
        return self._job_state & flag

    def set_job_state_flag(self, flag, state):
        if state: self._job_state |= flag
        else: self._job_state &= ~flag

