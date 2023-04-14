
'''
This will be a testing agent that will be able to play a simple
game of poker while providing a single output everytime.
'''

from PokerRL.rl.base_cls import EvalAgentBase


class rulesBased(EvalAgentBase):

    def get_action_frac_tuple(self, step_env=True):
        """
        Args:
            step_env (bool):        Whether the internal env shall be stepped

        Returns:
            2-tuple:  ((FOLD CALL or RAISE), fraction)
        """
        return ((1))